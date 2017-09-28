User Guide

* To use this tool, you can use only Java to test your web applications under Selenium2/WebDriver. Maybe you can support python in this tool yourself.

* Please checkout the project with encoding [UTF-8], otherwise you will get an ugly GUI in your desktop. Because the original project workspace was set to [UTF-8]. As known to us, different charsets take different length it the window. 
	<img src="https://github.com/fudax/selenium_recorder/blob/master/doc/1.png"></img>
	<img src="https://github.com/fudax/selenium_recorder/blob/master/doc/2.png"></img>
  
* Start to use this tool.
	
	>a) The long text area is used to put some texts used frequently, such as web address, user name and some testdata. Also, you can change the [./config/form_anounce] file, when this tool starts, the texts will be put here too.

	>b) Input start url into the text edit below, marked as [1]. Choose a style to save your scripts, such as bot-style, primal-style. There is no way to support page object, if you want to use it, please rebuild your script after you record them. This step is mark as [2] in the pictrue below.

	>c) Click the [Start Record] button, marked as [3] in the pictrue below. The [Start Page] tab will transfer to [Record Steps] tab automaticly. And the webdirver will start at the same while, navigating to the given url. Certainly, if the url is null, the tool will warn you until you input it even if it is incorrect.
	
	<img src="https://github.com/fudax/selenium_recorder/blob/master/doc/3.png"></img>
 
* How to record script.
	<img src="https://github.com/fudax/selenium_recorder/blob/master/doc/4.png"></img>
	
	>a) Press the [Record [Drag To Object]] button and drag to the object you want to operate. For example, we can drag the button to the search text edit in the first page of baidu(marked as [4] in the pictrue above).
    
	>b) When you release the button, you will find the script text area changes: the script has already generated in the area marked as [5] in the pictrue above.
    
	>c) You can change the attributes to change the way to locate the web elements. Also you can change the operator to change the operation to the object. Above all, the test script will be changed automaticly when the selected item changed.
    
	>d) You can input testdata value yourself in the text field , and the script will be changed automaticly too.
    
	>e) Then you can click [Test/Run Current Step] button to test if the script works. The tool was designed in this way is also because the browser manul operations may be disabled when webdriver runs.
    
	>f) If the script works, you can click the [Save Current Step] button to add the current step to the scripts library.
    
	>g) The [Switch To Window] button is used for multi-windows operation. We suggest that don’t let tests popup more than two windows. Because too many windows make bad sense to both customers/users and test toos ^_^. Before you switch to specified window, you should choose the correct window in the picklist [windows].
	
	<img src="https://github.com/fudax/selenium_recorder/blob/master/doc/5.png"></img>
 
* When you complete your recording job, you can click [Copy To Clipboard] then paste to your test classed.

	<img src="https://github.com/fudax/selenium_recorder/blob/master/doc/6.png"></img>
