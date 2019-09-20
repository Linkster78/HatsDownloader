# Patch Description
The patch method consists of taking a class from the original mod, decompiling it, editing the code, recompiling it and re-adding it to the .jar file.

## Lazy-man's guide
If you trust me to not give you junk, simply follow these two steps.
1. Grab the `Hats-4.0.1-prepatched` jarfile, add it to your mods folder.
2. Profit

## Patching pre-requisites
- WinRAR or 7zip or ...
- A copy of the Hats 4.0.1 mod jarfile
- A copy of the ThreadHatsReader.class file found in this repository folder

## Patching the mod yourself
1. Make a copy of Hats-4.0.1.jar
2. Open it with WinRAR or other software
3. Delete the `META-INF` folder (Used to tell whether the file is original)
4. Navigate to `hats/common/thread` folder
5. Drag and drop the `ThreadHatsReader.class` into the folder to overwrite the original one
6. Profit! Your jarfile is now patched and should download hats as it should