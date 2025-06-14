#!/usr/bin/bash

set -e

INSTALL_P=false
RUN_P=false

TERMUX_P=false

YELLOW="\e[33m"
LGREEN="\e[92m"
BLUE="\e[34m"
LMAGENTA="\e[95m"
RESET="\e[0m"

print_help_and_close() {
  echo -e "$BLUE Script to compile & install dae"
  echo -e "$RESET"
  echo "Usage Make.sh <option>"
  echo "Options:"
  echo -e "$LGREEN-i or --install | $LMAGENTA Compile and install."
  echo -e "$LGREEN-r or --run     | $LMAGENTA Compile and run."
  echo -e "$LGREEN-t or --termux  | $LMAGENTA [USE WITH -r] Compile and run it fixing termux restrictions."
  echo -e "$LGREEN-h or --help    | $LMAGENTA Prints help."
  echo ""
  echo -e "$YELLOW"
  echo "WARNING"
  echo "Dont use install and run command together."
  echo -e "$RESET"
  exit 0
}

for arg in "$@"; do
  case "$arg" in
    -i) INSTALL_P=true ;;
    --install) INSTALL_P=true ;;
    -r) RUN_P=true ;;
    --run) RUN_P=true ;;
    -t) TERMUX_P=true ;;
    --termux) TERMUX_P=true ;;
    -h) print_help_and_close ;;
    --help) print_help_and_close ;;
    *) print_help_and_close ;;
  esac
done

export INSTALL=$INSTALL_P

mkdir -p build
cd build

find ../src -name "*.java" > sources.txt

javac -d . --release 17 @sources.txt

if [ "$INSTALL_P" = true ]; then
  mkdir -p $HOME/../usr/share/dae
  cp -r * $HOME/../usr/share/dae
fi

if [ "$INSTALL_P" = false ]; then
  if [ "$RUN_P" = true ]; then
    if [ "$TERMUX_P" = true ]; then
      chmod +x .
    fi
    java dae.Main
  fi
fi