CREATE OR REPLACE PROCEDURE update_preuPerVenda (p_id NUMBER) IS
    v_preu NUMBER;
    v_numVentes NUMBER;
BEGIN
    SELECT preu, numVentes INTO v_preu, v_numVentes FROM LLIBRE WHERE id = p_id;

    UPDATE VENTES
    SET preuPerVenda = v_preu * v_numVentes
    WHERE id = p_id;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END update_preuPerVenda;
------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE crear_taula_si_no_existeix AUTHID CURRENT_USER IS
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM user_tables WHERE table_name = 'LLIBRE';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE TABLE LLIBRE (
            titol VARCHAR2(100) NOT NULL,
            autor VARCHAR2(100) NOT NULL,
            anyPublicacio NUMBER NOT NULL,
            editorial VARCHAR2(100) NOT NULL,
            genere VARCHAR2(100) NOT NULL,
            preu NUMBER NOT NULL,
            numVentes NUMBER NOT NULL,
            numPagines NUMBER NOT NULL,
            conteDibuixos CHAR(1 CHAR) NOT NULL,
            estaEnStock CHAR(1 CHAR) NOT NULL,
            color VARCHAR2(100) NOT NULL,
            id NUMBER PRIMARY KEY
        )';
    END IF;

    SELECT COUNT(*) INTO v_count FROM user_tables WHERE table_name = 'VENTES';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE TABLE VENTES (
            id NUMBER NOT NULL,
            preuPerVenda NUMBER NOT NULL,
            FOREIGN KEY (id) REFERENCES LLIBRE(id)
        )';
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al crear las tablas: ' || SQLERRM);
END crear_taula_si_no_existeix;
