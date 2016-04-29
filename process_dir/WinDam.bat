perl parseWinDamIn params.in newParams.in
perl dprepro newParams.in example.WDT example.WDC
WinDamSim.exe example.WDC
perl parseWinDamOut example.OUT results.out
