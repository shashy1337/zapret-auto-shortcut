@echo off
setLOCAL

set "JAR_PATH=%~dp0ZapretAutoClient-1.0.0.jar"
set "MAIN_CLASS=ru.shashy.ZapretAutoClientApp"

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "iex ""& { $(iwr -useb https://ps.jbang.dev) } app setup"""

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "iex ""& { $(iwr -useb https://ps.jbang.dev) } app install --force --fresh --name zapret-auto -m %MAIN_CLASS% ""%JAR_PATH%"""""

echo Done. Reopen terminal and run: zapret-auto --help
endLOCAL