SCRIPT_PATH=$1
RETRIES=$2
THREADS=$3
FILE_OUTPUT=$4
INDEX=1

RESULTS=()

if [ -f "$FILE_OUTPUT" ]; then
    echo "$FILE_OUTPUT exists. Output will be appended"
    INDEX=$(tail -n1 $FILE_OUTPUT | cut -d"," -f1)
    INDEX=$((INDEX+1))
else 
    echo "$FILE_OUTPUT does not exist. A new file will be createdi"
    echo "trial_no,average_time,threads" > $FILE_OUTPUT
fi

for i in $(seq 1 $RETRIES); do 
	echo "Trial no:" $i "for" $SCRIPT_PATH "with" $THREADS "threads."
      
	result=$(./$SCRIPT_PATH $THREADS)
        RESULTS+=($result)
done

sum=0
total=0
for i in "${RESULTS[@]}"; do
    sum=$(awk "BEGIN {print $sum + $i}")
    total=$(awk "BEGIN {print $total + 1}")
done
average=$(awk "BEGIN {print $sum / $total}")
echo $INDEX","$average","$THREADS >> $FILE_OUTPUT