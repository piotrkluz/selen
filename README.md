# selen
Lightweight selenium elements wrapper with powerful API

# Attention
Selen currently is in alpha version. Not ready for production. 

# Quick introduction
Selen extend and simplify Selenium API, and also handles Browser and driver running. 
So only one line is needed to run the browser and search the elements: 
```
import static SelenApi.$;
import static SelenApi.getDriver;
// inside main function or elsewhere in the code

WebDriver driver = getDriver()
driver.get("https://www.wikipedia.org/")

String bodyContent = $("html > body").outerHTML()
System.out.println(bodyContent)
```
Inside of `$("")` is selector.
Can be use both CSS and XPATH. Selen automatically recognizes what type is it. 

After run above code Selen will automatically download `chromedriver.exe` and run the browser on local computer. 

Selectors can be craated in chains (no matter if it's XPATH or CSS): 
SMatcher myInput = `$("body > div.content").$("//ul").$("//input[contains(.'some text')]")`
 
Selectors are lazy, after run above code nothing will happen with the browser. 

It will happen only after do an ACTION (for example click, get text etc.)
`myInput.click().type("[Ctrl+A][Delete]my new text")`
...yes above is keyboard key combinations - simple as that.

So if Selectors are executed only after actions we can easily create PageObjects and don't worry about run the code before open browser (or user not on page OR something)

So example page object will look like:
Does not need any special class, special constructos or init elements. All is initialized in pure JAVA
```
class MyForm  {
    SMatcher myInput = $("form input")
    SMatcher myMySelect = $("form select")
    SMatcher sendIt = $("//button[@text()='send']")
    
    public void sendIt(String value) {
        myInput.sendKeys(value);
        sendIt.click();
    }
}
```

More detailed info are in documentation (not yet created).





# Tasks to do: 
- [x] Main Matcher / Selector functionality 
- [x] Modules functionality
- [x] Extended WebElement API
- [x] Extended Keyboard API
- [x] Reuse of browser between runs
- [ ] Documentation
- [ ] Configuration of browser and other behavior (Currently it's always Chrome)
- [ ] Detailed Exceptions
- [ ] Tracing API
  - measuring performance
  - getting metadata for reports
- [x] Common modules: 
  - [x] Generic tables
  - [ ] Form fields
  
