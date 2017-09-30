CREATE TABLE testing(
    id VARCHAR(6) NOT NULL,
    fname VARCHAR(5)
);

INSERT INTO testing VALUES
    ('TKDF', 'JOHN'),
    ('IUF2', 'ANDRE'),
    ('VRE', 'TIM'),
    ('RTFD1', 'ALEX');


SELECT id FROM testing;
