Set WshShell = CreateObject("WScript.Shell")
WshShell.Run chr(34) & "C:\SHARE\mailSend\bpbgmail.bat" & Chr(34), 0
Set WshShell = Nothing