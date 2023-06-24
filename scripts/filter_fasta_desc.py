import parse_fasta
from Bio import SeqIO

def parse_fasta(fasta):
    for record in SeqIO.parse(fasta, "fasta"):
        seq_id = record.id
        seq_data = str(record.seq)
        yield(seq_id, seq_data)

if __name__ == '__main__':
    fasta_file = '../dataset/train_sequences.fasta'
    parser = parse_fasta(fasta_file)

    id_dict = {}
    with open('../dataset/filtered_train.fasta', 'w') as fasta_f:
        for seq_id, seq_data in parser:
            if seq_id in id_dict:
                suffix = 0
                while True:
                    tmp_seq_id = f"{seq_id}_{suffix}"
                    if tmp_seq_id not in id_dict:
                        seq_id = tmp_seq_id
                        print(seq_id)
                        break
                    suffix += 1
            fasta_f.write('>')
            fasta_f.write(seq_id)
            fasta_f.write('\n')
            fasta_f.write(seq_data)
            fasta_f.write('\n')
            id_dict[seq_id] = True


