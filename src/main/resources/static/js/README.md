# VNPay Timer Fix

This JavaScript file fixes the "timer is not defined" error that occurs in VNPay's payment page.

## Error Description

The error occurs in VNPay's custom.min.js file, specifically in the updateTime function:

```
jQuery.Deferred exception: timer is not defined ReferenceError: timer is not defined
    at updateTime (https://sandbox.vnpayment.vn/paymentv2/Scripts/custom.min.js:1:1651)
    at HTMLDocument.<anonymous> (https://sandbox.vnpayment.vn/paymentv2/Scripts/custom.min.js:1:1516)
    at e (https://sandbox.vnpayment.vn/paymentv2/Scripts/vendors/jquery/jquery.bundles.js:1:30038)
    at https://sandbox.vnpayment.vn/paymentv2/Scripts/vendors/jquery/jquery.bundles.js:1:30340 undefined
```

## Solution

The `vnpay-timer-fix.js` file initializes the timer variable before VNPay's custom.min.js tries to use it, preventing the error.

## How to Use

### Backend Integration

The JavaScript file is served by the backend through the following endpoint:

```
http://localhost:8080/api/v1/resources/vnpay-timer-fix.js
```

### Frontend Integration

To include this JavaScript file in your frontend application, add the following script tag to your payment page or component:

```html
<!-- Add this before any VNPay scripts -->
<script src="http://localhost:8080/api/v1/resources/vnpay-timer-fix.js"></script>
```

Make sure to add this script tag before any VNPay scripts are loaded.

### React Integration

If you're using React, you can add the script dynamically in your payment component:

```jsx
import { useEffect } from 'react';

function PaymentComponent() {
  useEffect(() => {
    // Add the VNPay timer fix script
    const script = document.createElement('script');
    script.src = 'http://localhost:8080/api/v1/resources/vnpay-timer-fix.js';
    script.async = true;
    document.head.appendChild(script);

    // Clean up
    return () => {
      document.head.removeChild(script);
    };
  }, []);

  // Rest of your component
}
```

### Vue.js Integration

If you're using Vue.js, you can add the script in your payment component:

```vue
<script>
export default {
  mounted() {
    // Add the VNPay timer fix script
    const script = document.createElement('script');
    script.src = 'http://localhost:8080/api/v1/resources/vnpay-timer-fix.js';
    script.async = true;
    document.head.appendChild(script);
  },
  beforeDestroy() {
    // Clean up
    const script = document.querySelector('script[src="http://localhost:8080/api/v1/resources/vnpay-timer-fix.js"]');
    if (script) {
      document.head.removeChild(script);
    }
  }
}
</script>
```

## Testing

To test if the fix is working:

1. Include the script in your frontend as described above
2. Proceed with the VNPay payment flow
3. Check the browser console for any errors
4. You should see the message "VNPay timer initialized" in the console, indicating that the fix is working

If you still encounter issues, please check that:
- The script is loaded before VNPay's scripts
- The backend is running and accessible from the frontend
- The security configuration allows access to the JavaScript endpoint