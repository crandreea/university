import subprocess
import yaml
import os
import csv

IMPLEMENTATION_MAP = {
    "async": "mpirun --oversubscribe -np {processes} ./cpp/cmake-build-debug/async",
    "scatter": "mpirun --oversubscribe -np {processes} ./cpp/cmake-build-debug/scatter",
    "standard": "mpirun --oversubscribe -np {processes} ./cpp/cmake-build-debug/standard",
    "sequential": "./cpp/cmake-build-debug/sequential",
    "standard_optimized": "mpirun --oversubscribe -np {processes} ./cpp/cmake-build-debug/standard_optimized"
}

NUM_RUNS = 1


def runner():
    cwd = os.getcwd()
    config_path = os.path.join(cwd, "configs", "runners.yaml")
    csv_path = os.path.join(cwd, "output", "results.csv")
    generated_path = os.path.join(cwd, "generated")
    results_path = os.path.join(cwd, "output", "sums")

    os.makedirs(os.path.dirname(csv_path), exist_ok=True)
    os.makedirs(generated_path, exist_ok=True)
    os.makedirs(results_path, exist_ok=True)

    if not os.path.exists(config_path):
        print(f"Config file not found: {config_path}")
        return

    with open(config_path, "r") as file:
        config = yaml.safe_load(file)

    subprocess.run(["./scripts/compiler.sh"], check=True)

    write_header = not os.path.exists(csv_path)
    with open(csv_path, "a", newline="") as csvfile:
        writer = csv.writer(csvfile)
        if write_header:
            writer.writerow(["implementation", "digits_1", "digits_2", "processes", "execution_time"])

        for impl in config.get("implementations", []):
            for impl_name, tests in impl.items():
                base_command = IMPLEMENTATION_MAP.get(impl_name)
                if base_command is None:
                    continue

                for test in tests:
                    for test_name, test_data in test.items():
                        digits_1 = test_data.get("digits_1")
                        digits_2 = test_data.get("digits_2")
                        processes_list = test_data.get("processes", [])

                        for digits in (digits_1, digits_2):
                            number_file = os.path.join(generated_path, f"number_{digits}.txt")
                            if not os.path.exists(number_file):
                                subprocess.run(["./scripts/generate_number.sh", str(digits)], check=True)

                        for processes in processes_list:
                            run_times = []
                            for _ in range(NUM_RUNS):
                                try:
                                    result_filename = f"result_{impl_name}_{digits_1}_{digits_2}_p{processes}.txt"
                                    result_filepath = os.path.join(results_path, result_filename)

                                    if impl_name == "sequential":
                                        cmd = [
                                            base_command,
                                            os.path.join(generated_path, f"number_{digits_1}.txt"),
                                            os.path.join(generated_path, f"number_{digits_2}.txt"),
                                            result_filepath
                                        ]
                                    else:
                                        cmd = (
                                            base_command.format(processes=processes)
                                            + f" {os.path.join(generated_path, f'number_{digits_1}.txt')}"
                                            + f" {os.path.join(generated_path, f'number_{digits_2}.txt')}"
                                            + f" {result_filepath}"
                                        )
                                        cmd = cmd.split()

                                    result = subprocess.run(
                                        cmd,
                                        capture_output=True,
                                        text=True,
                                        check=True
                                    )
                                    output = result.stdout.strip()
                                    if output:
                                        run_times.append(float(output))
                                    else:
                                        run_times.append(-1)
                                except subprocess.CalledProcessError as e:
                                    print(f"Error running {base_command}: {e}")
                                    run_times.append(-1)

                            valid_times = [t for t in run_times if t >= 0]
                            avg_time = sum(valid_times) / len(valid_times) if valid_times else -1

                            print(f"{impl_name:<12} {digits_1:<8} {digits_2:<8} {processes:<4} {avg_time:<5.2f}")
                            writer.writerow([impl_name, digits_1, digits_2, processes, avg_time])


if __name__ == "__main__":
    runner()