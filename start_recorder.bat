if "%1" == "h" goto begin 
mshta vbscript:createobject("wscript.shell").run("%~nx0 h",0)(window.close)&&exit 
:begin
java -jar -Dfile.encoding=utf-8 selenium-recorder-1.0.jar