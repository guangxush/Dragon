# -*- coding: utf-8 -*-
import codecs
import json
import os
import pickle

import jieba
import numpy as np
from keras_bert import Tokenizer
from chapter01.config import Config
from chapter01.util.utils import pad


def load_tags(tag_file):
    tags2id = {}
    id2tags = {}
    with codecs.open(os.path.join('data', tag_file), encoding='utf-8') as f:
        for line in f:
            tags2id[line.strip()] = len(tags2id)
            id2tags[len(id2tags)] = line.strip()
    return tags2id, id2tags


def load_data(raw_file):
    # 统计字符串长度
    tags2id, id2tags = load_tags('tags1.txt')
    tags2id2, id2tags2 = load_tags('tags2.txt')
    with open('data/word_level/vocabulary_all.pkl', 'rb') as f_vocabulary:
        vocabulary = pickle.load(f_vocabulary)
    print('vocab_len_word:', len(vocabulary))
    with open('data/char_level/vocabulary_all.pkl', 'rb') as f_vocabulary:
        vocabulary_char = pickle.load(f_vocabulary)
    print('vocab_len_char:', len(vocabulary_char))
    x_sent = list()
    x_label = list()
    x_label2 = list()
    x_sent_char = list()
    max_len = 0.
    max_char_len = 0.
    avg_len = 0.
    avg_char_len = 0.
    with codecs.open(raw_file, encoding='utf-8') as f:
        for line in f:
            x = json.loads(line)
            input_sent = x['content']

            words_sent = jieba.lcut(input_sent)
            x_sent.append([vocabulary.get(word, len(vocabulary) + 1) for word in words_sent])
            try:
                labels = x['intents'][0]['action']['value'].strip()
                y = [0] * len(tags2id)
                y[tags2id[labels]] = 1
                x_label.append(y)
            except (IndexError, KeyError):
                y = [0] * len(tags2id)
                y[0] = 1
                x_label.append(y)
            try:
                labels2 = x['intents'][0]['target']['value'].strip()
                y = [0] * len(tags2id2)
                y[tags2id2[labels2]] = 1
                x_label2.append(y)
            except (IndexError, KeyError):
                y = [0] * len(tags2id2)
                y[0] = 1
                x_label2.append(y)
            if len(words_sent) > max_len:
                max_len = len(words_sent)
            avg_len += len(words_sent)
            l = 0.
            for word in words_sent:
                if len(word) > max_char_len:
                    max_char_len = len(word)
                l += len(word)
            avg_char_len += l / len(words_sent)
            x_sent_char.append(
                [[vocabulary_char.get(char, len(vocabulary_char) + 1) for char in word] for word in words_sent])
    print('max_len:', max_len, 'max_char_len:', max_char_len)
    print('avg_len:', avg_len / len(x_sent_char), 'avg_char_len:', avg_char_len / len(x_sent_char))
    x_label = np.asarray(x_label)
    x_label2 = np.asarray(x_label2)
    return x_sent, x_sent_char, x_label, x_label2, vocabulary


def load_single_label(x, tags2id):
    y = [0] * len(tags2id)
    try:
        action = x['intents'][0]['action']['value'].strip()
        target = x['intents'][0]['target']['value'].strip()
        labels = action + '-' + target
        y[tags2id[labels]] = 1
    except (IndexError, KeyError):
        y[0] = 1  # 视情况而定
    return y


def load_multi_label(x, tags2id, tags2id2):
    y1 = [0] * len(tags2id)
    y2 = [0] * len(tags2id2)
    try:
        labels = x['intents'][0]['action']['value'].strip()
        y1[tags2id[labels]] = 1
    except (IndexError, KeyError):
        y1[0] = 1
    try:
        labels2 = x['intents'][0]['target']['value'].strip()
        y2[tags2id2[labels2]] = 1
    except (IndexError, KeyError):
        y2[0] = 1
    return y1, y2


def load_word_data(raw_file, label_level='single', train=True):
    config = Config()
    if label_level == 'single':
        tags2id, id2tags = load_tags('tags.txt')
        with open('data/word_level/vocabulary_all.pkl', 'rb') as f_vocabulary:
            vocabulary = pickle.load(f_vocabulary)
        print('vocab_len_word:', len(vocabulary))
        x_sent = list()
        x_label = list()
        with codecs.open(raw_file, encoding='utf-8') as f:
            for line in f:
                x = json.loads(line)
                input_sent = x['content']
                words_sent = jieba.cut(input_sent)
                x_sent.append([vocabulary.get(word, len(vocabulary) + 1) for word in words_sent])
                if train:
                    y = load_single_label(x, tags2id)
                    x_label.append(y)
        x_label = np.asarray(x_label)
        x_sent = pad(x_sent, 'word', config.max_len_word)
        return x_sent, x_label, id2tags, vocabulary
    elif label_level == 'multi':
        tags2id, id2tags = load_tags('tags.txt')
        tags2id2, id2tags2 = load_tags('tags2.txt')
        with open('data/word_level/vocabulary_all.pkl', 'rb') as f_vocabulary:
            vocabulary = pickle.load(f_vocabulary)
        print('vocab_len_word:', len(vocabulary))
        x_sent = list()
        x_label = list()
        x_label2 = list()
        with codecs.open(raw_file, encoding='utf-8') as f:
            for line in f:
                x = json.loads(line)
                input_sent = x['content']
                words_sent = jieba.cut(input_sent)
                x_sent.append([vocabulary.get(word, len(vocabulary) + 1) for word in words_sent])
                if train:
                    y1, y2 = load_multi_label(x, tags2id, tags2id2)
                    x_label.append(y1)
                    x_label2.append(y2)
        x_label = np.asarray(x_label)
        x_label2 = np.asarray(x_label2)
        x_sent = pad(x_sent, 'word', config.max_len_word)
        return x_sent, x_label, x_label2, id2tags, id2tags2, vocabulary
    else:
        return None


def load_char_data(raw_file, label_level='single', train=True):
    config = Config()
    if label_level == 'single':
        tags2id, id2tags = load_tags('tags.txt')
        with open('data/word_level/vocabulary_all.pkl', 'rb') as f_vocabulary:
            vocabulary = pickle.load(f_vocabulary)
        print('vocab_len_word:', len(vocabulary))
        with open('data/char_level/vocabulary_all.pkl', 'rb') as f_vocabulary:
            vocabulary_char = pickle.load(f_vocabulary)
        print('vocab_len_char:', len(vocabulary_char))
        x_sent = list()
        x_label = list()
        x_sent_char = list()
        with codecs.open(raw_file, encoding='utf-8') as f:
            for line in f:
                x = json.loads(line)
                input_sent = x['content']
                words_sent = jieba.lcut(input_sent)
                x_sent.append([vocabulary.get(word, len(vocabulary) + 1) for word in words_sent])
                x_sent_char.append(
                    [[vocabulary_char.get(char, len(vocabulary_char) + 1) for char in word] for word in words_sent])
                if train:
                    y = load_single_label(x, tags2id)
                    x_label.append(y)
        x_label = np.asarray(x_label)
        x_sent = pad(x_sent, 'word', config.max_len_word)
        x_sent_char = pad(x_sent_char, 'char', config.max_len_word, config.char_per_word)
        return x_sent, x_sent_char, x_label, id2tags, vocabulary
    elif label_level == 'multi':
        tags2id, id2tags = load_tags('tags.txt')
        tags2id2, id2tags2 = load_tags('tags2.txt')
        with open('data/word_level/vocabulary_all.pkl', 'rb') as f_vocabulary:
            vocabulary = pickle.load(f_vocabulary)
        print('vocab_len_word:', len(vocabulary))
        with open('data/char_level/vocabulary_all.pkl', 'rb') as f_vocabulary:
            vocabulary_char = pickle.load(f_vocabulary)
        print('vocab_len_char:', len(vocabulary_char))
        x_sent = list()
        x_label = list()
        x_label2 = list()
        x_sent_char = list()
        with codecs.open(raw_file, encoding='utf-8') as f:
            for line in f:
                x = json.loads(line)
                input_sent = x['content']
                words_sent = jieba.lcut(input_sent)
                x_sent.append([vocabulary.get(word, len(vocabulary) + 1) for word in words_sent])
                x_sent_char.append(
                    [[vocabulary_char.get(char, len(vocabulary_char) + 1) for char in word] for word in words_sent])
                if train:
                    y1, y2 = load_multi_label(x, tags2id, tags2id2)
                    x_label.append(y1)
                    x_label2.append(y2)
        x_label = np.asarray(x_label)
        x_label2 = np.asarray(x_label2)
        x_sent = pad(x_sent, 'word', config.max_len_word)
        x_sent_char = pad(x_sent, 'char', config.max_len_word, config.char_per_word)
        return x_sent, x_sent_char, x_label, x_label2, id2tags, id2tags2, vocabulary
    else:
        return None


def load_bert_data(raw_file, label_level='single', train=True):
    config = Config()
    dict_path = './corpus/vocab.txt'
    token_dict = {}
    with codecs.open(dict_path, 'r', 'utf8') as reader:
        for line in reader:
            token = line.strip()
            token_dict[token] = len(token_dict)
    if label_level == 'single':
        tags2id, id2tags = load_tags('tags.txt')
        x_ids = list()
        x_segments = list()
        x_label = list()
        with codecs.open(raw_file, encoding='utf-8') as f:
            for line in f:
                x = json.loads(line)
                input_sent = x['content']
                tokenizer = Tokenizer(token_dict)
                x_sent_id, x_sent_segment = tokenizer.encode(input_sent, max_len=config.max_len_word)
                x_ids.append(x_sent_id)
                x_segments.append(x_sent_segment)
                if train:
                    y = load_single_label(x, tags2id)
                    x_label.append(y)
        x_label = np.asarray(x_label)
        return x_ids, x_segments, x_label, id2tags, None
    elif label_level == 'multi':
        tags2id, id2tags = load_tags('tags.txt')
        tags2id2, id2tags2 = load_tags('tags2.txt')
        x_ids = list()
        x_segments = list()
        x_label = list()
        x_label2 = list()
        with codecs.open(raw_file, encoding='utf-8') as f:
            for line in f:
                x = json.loads(line)
                input_sent = x['content']
                tokenizer = Tokenizer(token_dict)
                x_sent_id, x_sent_segment = tokenizer.encode(input_sent, max_len=config.max_len_word)
                x_ids.append(x_sent_id)
                x_segments.append(x_sent_segment)
                if train:
                    y1, y2 = load_multi_label(x, tags2id, tags2id2)
                    x_label.append(y1)
                    x_label2.append(y2)
        x_label = np.asarray(x_label)
        return x_ids, x_segments, x_label, id2tags, None
    else:
        return None


if __name__ == '__main__':
    load_data('data/train_data.txt')