
from Bio import SeqIO

fasta_file = '../dataset/train_sequences.fasta'

for record in SeqIO.parse(fasta_file, "fasta"):
    seq_id = record.id
    seq_data = str(record.seq)
    print(seq_id, seq_data)
    break
