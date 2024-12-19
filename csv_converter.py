import csv

csv.field_size_limit(100000000)
def convert_csv_to_txt(csv_filename, txt_filename):
    with open(csv_filename, mode="r") as csv_file:
        csv_reader = csv.reader(csv_file)
        with open(txt_filename, mode="w") as txt_file:
            for row in csv_reader:
                txt_file.write("\t".join(row) + "\n")


convert_csv_to_txt("PresortedRandomInput.csv", "PresortedRandominput.txt")
