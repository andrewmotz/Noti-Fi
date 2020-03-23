# Noti-Fi
## About
This app allows you to create custom notifications that are triggered when you connect certain WiFi networks.

This allows for location based reminders without the use of GPS or someone tracking you 24/7 and selling all your location data.

We created this app for a school project but also wanted to use the app for ourselves and whoever else is interested.

## Privacy
This app does not keep track of location or use any GPS data. The only reason it requires location permissions in the foreground and background is because the app needs to read the WiFi network names. Google does not allow apps to get the name without location permissions. When you add a new Noti-Fi it stores the network's name and once your phone connects to a network. It compares the just connected network's name to your saved Noti-Fis and if any network names match, you get a notification.
