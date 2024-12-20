import csv

csv.field_size_limit(100000000)
def convert_csv_to_txt(csv_filename, txt_filename):
    with open(csv_filename, mode="r") as csv_file:
        csv_reader = csv.reader(csv_file)
        with open(txt_filename, mode="w") as txt_file:
            for row in csv_reader:
                txt_file.write("\t".join(row) + "\n")


def convert_txt_to_csv(txt_filename, csv_filename):
    with open(txt_filename, mode="r") as txt_file:
        txt_reader = txt_file.readlines()  # Read all lines from the txt file
        with open(csv_filename, mode="w", newline="") as csv_file:
            csv_writer = csv.writer(csv_file)
            for line in txt_reader:
                csv_writer.writerow(line.strip().split("\t"))


# ex
convert_csv_to_txt("PresortedRandomInput.csv", "PresortedRandominput.txt")
convert_txt_to_csv("PresortedRandominput.txt", "PresortedRandomInput.csv")
