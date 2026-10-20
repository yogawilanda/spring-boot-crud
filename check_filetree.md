cheatcode: untuk check filetree

Clear-Host

$root = "F:\Coding\java\ProjectMasEvan\demo\demo"

if (-not (Test-Path -LiteralPath $root -PathType Container)) {
    Write-Host "PROJECT TIDAK DITEMUKAN: $root" -ForegroundColor Red
    return
}

function Show-Tree {
    param(
        [string]$Path,
        [string]$Prefix = "",
        [string[]]$Exclude = @("target", ".git", ".idea", ".vscode")
    )

    $items = @(
        Get-ChildItem -LiteralPath $Path -Force |
        Where-Object { $_.Name -notin $Exclude } |
        Sort-Object @{ Expression = { -not $_.PSIsContainer } }, Name
    )

    for ($i = 0; $i -lt $items.Count; $i++) {
        $item = $items[$i]
        $last = $i -eq ($items.Count - 1)
        $branch = if ($last) { "\-- " } else { "+-- " }

        $suffix = if ($item.PSIsContainer) { "\" } else { "" }
        Write-Output "$Prefix$branch$($item.Name)$suffix"

        if ($item.PSIsContainer) {
            $nextPrefix = $Prefix + $(if ($last) { "    " } else { "|   " })
            Show-Tree -Path $item.FullName -Prefix $nextPrefix -Exclude $Exclude
        }
    }
}

Write-Host "=== PROJECT FILE TREE ===" -ForegroundColor Cyan
Write-Host $root
$tree = Show-Tree -Path $root
$tree | Out-Host

Write-Host "`n=== LOCAL BACKUP SUMMARY ===" -ForegroundColor Cyan
$backup = Join-Path $root "local_backup"

if (Test-Path -LiteralPath $backup) {
    Get-ChildItem -LiteralPath $backup -Force -Recurse |
        Select-Object @{
            Name = "Type"
            Expression = { if ($_.PSIsContainer) { "DIR" } else { "FILE" } }
        }, @{
            Name = "RelativePath"
            Expression = { $_.FullName.Substring($root.Length + 1) }
        } |
        Format-Table -AutoSize -Wrap
} else {
    Write-Host "Folder local_backup tidak ditemukan." -ForegroundColor Yellow
}

Write-Host "`n=== ROOT FILES THAT LOOK LIKE BACKUPS ===" -ForegroundColor Cyan
Get-ChildItem -LiteralPath $root -Force |
    Where-Object { $_.Name -match 'backup|\.bak' } |
    Select-Object Name, Mode

$report = @(
    "=== PROJECT FILE TREE ==="
    $root
    $tree
) -join "`r`n"

$report | Set-Clipboard
Write-Host "`nFile tree disalin ke clipboard. Paste hasilnya ke sini." -ForegroundColor Green