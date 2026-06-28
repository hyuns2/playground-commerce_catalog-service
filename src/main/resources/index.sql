ALTER TABLE products ADD FULLTEXT INDEX idx_ft_name(name) WITH PARSER ngram;
