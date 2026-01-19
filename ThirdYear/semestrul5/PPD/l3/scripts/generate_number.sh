#!/bin/zsh

if [ $# -ne 1 ]; then
  echo "Usage: $0 <number_of_digits>"
  exit 1
fi

readonly GENERATED_PATH="./generated"
mkdir -p "$GENERATED_PATH"

DIGITS=$1
FILE="$GENERATED_PATH/number_$DIGITS.txt"

if [ "$DIGITS" -le 0 ]; then
  echo "Number of digits must be greater than 0."
  exit 1
fi

if [ -f "$FILE" ]; then
  exit 0
fi

FIRST_DIGIT=$((RANDOM % 9 + 1))
NUMBER="$FIRST_DIGIT"

for ((i = 1; i < DIGITS; i++)); do
  DIGIT=$((RANDOM % 10))
  NUMBER="${NUMBER}${DIGIT}"
done

{
  echo "$DIGITS"
  echo "$NUMBER"
} > "$FILE"
