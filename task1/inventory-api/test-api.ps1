$startDateText = Read-Host "Enter start date (YYYY-MM-DD)"
$endDateText = Read-Host "Enter end date (YYYY-MM-DD)"
$outputFormat = Read-Host "Enter output format: JSON or TABLE"

$parsedStartDate = [datetime]::MinValue
$parsedEndDate = [datetime]::MinValue
$dateFormat = [Globalization.CultureInfo]::InvariantCulture

$validStartDate = [datetime]::TryParseExact(
    $startDateText,
    "yyyy-MM-dd",
    $dateFormat,
    [Globalization.DateTimeStyles]::None,
    [ref]$parsedStartDate)
$validEndDate = [datetime]::TryParseExact(
    $endDateText,
    "yyyy-MM-dd",
    $dateFormat,
    [Globalization.DateTimeStyles]::None,
    [ref]$parsedEndDate)

if (-not $validStartDate -or -not $validEndDate) {
    Write-Host "Invalid date. Use YYYY-MM-DD, for example 2024-01-01." -ForegroundColor Red
    exit 1
}

if ($parsedStartDate -gt $parsedEndDate) {
    Write-Host "Start date must be before or equal to end date." -ForegroundColor Red
    exit 1
}

$outputFormat = $outputFormat.ToUpperInvariant()
if ($outputFormat -ne "JSON" -and $outputFormat -ne "TABLE") {
    Write-Host "Invalid output format. Enter JSON or TABLE." -ForegroundColor Red
    exit 1
}

$apiUrl = "http://localhost:8081/api/inventory/details?startDate=$startDateText&endDate=$endDateText"

try {
    $response = Invoke-RestMethod -Uri $apiUrl -Method Get
    if ($null -eq $response -or $response.Count -eq 0) {
        Write-Host "No inventory records found between $startDateText and $endDateText." -ForegroundColor Yellow
    } elseif ($outputFormat -eq "TABLE") {
        $response | ForEach-Object {
            [pscustomobject]@{
                Id = $_.id
                PurchaseDate = $_.purchaseDate
                Cost = $_.cost
                InventoryDetails = ($_.inventoryDetails -join "; ")
            }
        } | Format-Table -AutoSize -Wrap
    } else {
        $response | ConvertTo-Json -Depth 5
    }
} catch {
    Write-Host "Could not contact the API at http://localhost:8081." -ForegroundColor Red
    Write-Host "Make sure the API is running, then try again." -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor DarkRed
    exit 1
}