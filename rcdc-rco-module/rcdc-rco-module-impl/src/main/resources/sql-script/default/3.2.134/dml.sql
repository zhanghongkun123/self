update t_rco_terminal_work_mode_mapping set platform = 'VOI' where support_mode in ('RG-CT5302C-G4','RG-CT5502C-G4','RG-CT5702C-G4','RG-CT5500C-CS');
update t_rco_terminal_work_mode_mapping set platform = 'IDV' where support_mode = 'RG-CT7900 V5.00';