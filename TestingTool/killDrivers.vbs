Dim oShell : Set oShell = CreateObject("WScript.Shell")

   sComputerName = "."
   Set objWMIService = GetObject("winmgmts:\\" & sComputerName & "\root\cimv2")
   sQuery = "SELECT * FROM Win32_Process"
   Set objItems = objWMIService.ExecQuery(sQuery)
   'iterate all item(s)
   For Each objItem In objItems
If (objItem.Name = "chromedriver.exe") Then
' WScript.Echo "Process [Name:" & objItem.Name & "]"  'Debugging log''
' Kill chromedriver '
oShell.Run "taskkill /f /im chromedriver.exe", , True
End If
   Next
   WScript.Echo "You've killed all Chromedrivers"