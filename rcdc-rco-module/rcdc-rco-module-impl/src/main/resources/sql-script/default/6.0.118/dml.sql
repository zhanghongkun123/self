--  UPM打印机配置恢复%USERPROFILE%\AppData\Roaming\Microsoft 配置，只是要去掉之前的\Credentials即可
INSERT INTO public.t_rco_user_profile_path_detail (id,"path",user_profile_child_path_id,user_profile_path_id,"index","version") VALUES
('e047c17f-e193-42db-bf11-a2f473821bd9'::uuid,'%USERPROFILE%\AppData\Roaming\Microsoft','51194b97-d1aa-4530-b962-c69f7294b943','9d3a8d54-8f3f-4e25-8edf-6f252f7805f4',0,0) ON CONFLICT(id) DO NOTHING;