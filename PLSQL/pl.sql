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
