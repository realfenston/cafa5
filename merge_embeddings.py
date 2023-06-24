import torch
import os
import numpy as np

from Bio import SeqIO


def parse_fasta(fasta):
    for record in SeqIO.parse(fasta, "fasta"):
        seq_id = record.id
        seq_data = str(record.seq)
        yield(seq_id, seq_data)

if __name__ == '__main__':
    fasta_file = './dataset/train_sequences.fasta'
    parser = parse_fasta(fasta_file)

    train_embeddings = None
    with open('./dataset/filtered_train.fasta', 'w') as fasta_f:
        for seq_id, seq_data in parser:
            if not os.path.exists(f'./embeddings/{seq_id}.pt'):
                print(seq_id)
                break
            pt_file = f'./embeddings/{seq_id}.pt'
            loaded_tensor = torch.load(pt_file)
            train_embeddings = loaded_tensor['mean_representations'][33].cpu().numpy() if train_embeddings is None else np.concatenate((train_embeddings, loaded_tensor['mean_representations'][33].cpu().numpy()))
            print(train_embeddings.shape)
            
    train_embeddings.reshape((-1, 2560))
    np.save('./train_embeddings.npy', train_embeddings)

            

