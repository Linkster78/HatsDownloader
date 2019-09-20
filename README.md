# About
This is a tool to fix the Hats mod by iChunUtil. The issue sometimes arises because the mod requests use HTTP, and not HTTPS which is now refused by the CDN providers. With this tool, you can download the hats directly from the CDN and make a hats folder out of it.

## Usage
Download the latest build from the [Releases](https://github.com/RedstoneTek/HatsDownloader/releases) tab.

Put it on your desktop or other location of your choice. Open a command prompt/command line/shell and run the command `java -jar <jarname.jar>`.

Once it's done, there should be a complete `hats` folder generated, simply drag and drop it into your FTB server or your minecraft installation!

## Patch Method
If you'd rather not have to rely on some shady software made by some third party (myself), you could patch the mod yourself to change it to HTTPS. More information is available [here](https://github.com/RedstoneTek/HatsDownloader/tree/master/patch).