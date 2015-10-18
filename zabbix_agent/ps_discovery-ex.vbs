Set stdout= Wscript.StdOut
'dim startTime, endTime
'startTime = Timer
 
strComputer = "." 
Set objWMIService = GetObject("winmgmts:\\" & strComputer & "\root\CIMV2") 
Set colItems = objWMIService.ExecQuery( _
    "SELECT Name, PercentProcessorTime, WorkingSet FROM Win32_PerfFormattedData_PerfProc_Process where Name like 'java.exe'") 


stdout.WriteLine  "{" & """data"":["

Comma=""
For Each psname in psToplistArray
    stdout.Write Comma
    stdout.Write "{""{#PROCNAME}"":""" 
    stdout.Write psname
    stdout.WriteLine """}"
    Comma=","
Next
stdout.WriteLine "]}"

'endTime = Timer
'stdout.WriteLine "running time is : " & (endTime - startTime)
stdout.Close


