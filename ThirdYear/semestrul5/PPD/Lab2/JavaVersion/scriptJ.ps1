#param (
#    [int]$threads        # număr de threaduri
#)

# Setări
$className = "ppd_org.Convolutie"
$classpath = "build/classes/java/main"
$outputFile = "output_paralele.txt"
$threads = 2
$runs = 10
$total = 0

Write-Host "Rulare $className cu $threads thread-uri, de $runs ori..."

for ($i = 1; $i -le $runs; $i++) {
    Write-Host "Rulare #$i"
    $result = java -cp $classpath $className $threads

    # Caută linia cu timpul de execuție
    $lastLine = $result | Select-Object -Last 1

    if ($lastLine -match '([0-9]+(\.[0-9]+)?)') {
        $time = [double]$matches[1]
        $total += $time
        Write-Host "   Timp: $time ms"
    } else {
        Write-Host "   Nu am găsit timp valid în output:"
        Write-Host $result
    }
}

# Calcul medie
$average = [math]::Round($total / $runs, 3)
Write-Host "`nTimp mediu: $average ms"

# Scrie rezultatul în fișier
"$threads,$average" | Out-File -FilePath $outputFile -Encoding UTF8 -Append
Write-Host "Rezultat salvat în $outputFile"
