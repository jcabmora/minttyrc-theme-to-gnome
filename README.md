# minttyrc-theme-to-gnome
Utility to migrate mintty themes into gnome.

Usage:

1) compile by running: 
  > javac GetMinttyTheme.java

2) run the program to generate a bash script from a mintty config file named "minttycolor":
  > java GetMinttyTheme minttycolor

   This will generate a file named minttycolor.sh.

3) depending on your umask settings, you might need to change the permissions on the generated bash file to allow you to execute the file:

  > chmod 555 minttycolor.sh

4) run the generate file and enjoy!

  > ./minttycolor.sh


Notes:
You can find many really good looking mintty themes here:
- https://github.com/mavnn/mintty-colors-solarized
- https://github.com/oumu/mintty-color-schemes





