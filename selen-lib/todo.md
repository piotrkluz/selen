# Bugs
- [ ] Wrong finding elements in modules: 
```html
<ul><li></li><li></li></ul>
<ul><li></li><li></li></ul>
```
in this code command `$('ul').$('li').count()` should return 2, but actual is 4

## Features
- [x] $().findAll()
    - map()
    - each()
    - filter()
    - anyMatch()
    - allMatch()


- [ ] Custom exceptions with proper messages
    - Keyboard exceptions
    - Find element exceptions
    - item not found exceptions
    - Standard selenium exceptions
    
- [ ] Forms classes / interfaces
    - Generic setValue(), getValue() on `AnyFormField`
    - Form.fill(ObjectModel / JSON)
    
- [ ] Catch events
    - measure performance
    - time tracing
    
- [ ] Add Global configuration
    - select browser name
    - custom WebDriver provider
    - merge selectors tweaks policy

- [ ] Regenerate already used driver in next session
    https://stackoverflow.com/a/51145789
  
- [ ] Parallel run tests
  https://junit.org/junit5/docs/snapshot/user-guide/#writing-tests-parallel-execution
