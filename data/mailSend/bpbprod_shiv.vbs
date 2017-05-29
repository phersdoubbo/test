Set WshShell = CreateObject("WScript.Shell")
WshShell.Run chr(34) & "C:\SHARE\mailSend\bpbprod_shiv.bat" & Chr(34), 0
Set WshShell = Nothing