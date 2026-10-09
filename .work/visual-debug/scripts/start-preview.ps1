$ErrorActionPreference = 'Stop'
$previewRoot = (Resolve-Path (Join-Path $PSScriptRoot '../../..')).Path
$previewUrl = 'http://127.0.0.1:8787/'
try {
    $response = Invoke-WebRequest $previewUrl -TimeoutSec 2
    if ($response.StatusCode -eq 200 -and $response.Content.Contains('LEETCODE LAB')) {
        Write-Output "本地预览已运行：$previewUrl"
        return
    }
} catch {}
$nodeExecutable = (Get-Command node -ErrorAction Stop).Source
$logDirectory = Join-Path $previewRoot '.work/visual-debug/logs'
New-Item -ItemType Directory -Path $logDirectory -Force | Out-Null
$process = Start-Process -FilePath $nodeExecutable -ArgumentList '.work/visual-debug/scripts/serve.mjs' -WorkingDirectory $previewRoot -WindowStyle Hidden -RedirectStandardOutput (Join-Path $logDirectory 'preview-server.stdout.log') -RedirectStandardError (Join-Path $logDirectory 'preview-server.stderr.log') -PassThru
for ($attempt = 0; $attempt -lt 20; $attempt++) {
    Start-Sleep -Milliseconds 200
    try {
        $response = Invoke-WebRequest $previewUrl -TimeoutSec 2
        if ($response.StatusCode -eq 200 -and $response.Content.Contains('LEETCODE LAB')) {
            Write-Output "本地预览已启动：$previewUrl（PID $($process.Id)）"
            return
        }
    } catch {}
    if ($process.HasExited) { break }
}
throw '预览启动失败，请查看 .work/visual-debug/logs/preview-server.stderr.log'
