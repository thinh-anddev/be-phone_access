// This script initializes the timer variable for VNPay's payment page
// to fix the "timer is not defined" error in the updateTime function

// Define the timer variable globally
var timer;

// Function to initialize the timer
function initializeTimer() {
    // Check if timer is already defined
    if (typeof timer === 'undefined' || timer === null) {
        // Initialize the timer with a default value
        timer = null;
        console.log('VNPay timer initialized');

        // Define updateTime function if it doesn't exist
        if (typeof updateTime !== 'function') {
            window.updateTime = function() {
                // This is a placeholder for VNPay's updateTime function
                // It will be overridden by VNPay's implementation
                console.log('Placeholder updateTime function called');
            };
            console.log('Placeholder updateTime function defined');
        }
    }
}

// Initialize the timer when the document is ready
document.addEventListener('DOMContentLoaded', function() {
    initializeTimer();
});

// Also initialize immediately in case the script is loaded after DOMContentLoaded
initializeTimer();

// Set up a MutationObserver to watch for changes to the DOM
// This helps ensure the timer is initialized even if the payment page is loaded dynamically
var observer = new MutationObserver(function(mutations) {
    initializeTimer();
});

// Start observing the document with the configured parameters
observer.observe(document, { childList: true, subtree: true });

// Ensure the timer is initialized when the window loads
window.addEventListener('load', function() {
    initializeTimer();
});
