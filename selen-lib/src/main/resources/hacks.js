// will be used in $(...).traceDOMChanges()

// Create an observer instance linked to the callback function
let obs = new MutationObserver(function(mutationsList, observer) {
    // Use traditional 'for loops' for IE 11
    for(const mutation of mutationsList) {
        if (mutation.type === 'childList') {
            console.log('A child node has been added or removed.');
        }
        else if (mutation.type === 'attributes') {
            console.log(`The '${mutation.attributeName}' attribute was modified.\nCURRENT: ${mutation.target.getAttribute(mutation.attributeName)}`);
            console.log(mutation)
        }
    }
});

// Start observing the target node for configured mutations
obs.observe(
    document.querySelector(".someClass"),
    { attributes: true, childList: true, subtree: true }
    );
// obs.disconnect();
