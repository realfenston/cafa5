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
    fasta_file = './dataset/testsuperset.fasta'
    parser = parse_fasta(fasta_file)

    embeddings = None
    with open('./dataset/filtered_test.fasta', 'w') as fasta_f:
        for seq_id, seq_data in parser:
            if not os.path.exists(f'./test_embeddings/{seq_id}.pt'):
                print(seq_id)
                break
            pt_file = f'./test_embeddings/{seq_id}.pt'
            loaded_tensor = torch.load(pt_file)
            embeddings = loaded_tensor['mean_representations'][36].cpu().numpy() if embeddings is None else np.concatenate((embeddings, loaded_tensor['mean_representations'][36].cpu().numpy()))
            print(embeddings.shape)
            
    embeddings = embeddings.reshape((-1, 2560))
    np.save('./test_embeddings.npy', embeddings)