def create_protein_ids(fasta_file):
    from Bio import SeqIO

    def parse_fasta(fasta):
        for record in SeqIO.parse(fasta, "fasta"):
            seq_id = record.id
            seq_data = str(record.seq)
            yield(seq_id, seq_data)
            
    parser = parse_fasta(fasta_file)
    train_ids = []
    for seq_id, _ in parser:
        train_ids.append(seq_id)
    
    import numpy as np
    np.save('train_ids.npy', np.array(train_ids))
    
if __name__ == "__main__":
    create_protein_ids('./dataset/filtered_train.fasta')