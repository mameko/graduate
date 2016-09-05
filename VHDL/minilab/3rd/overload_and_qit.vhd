library ieee;
use ieee.std_logic_1164.all;

package  my_package is
  type qit is ('x','0','1','z');
  function "and" (a,b : qit) return qit;
  function to_vector (val: integer) return bit_vector;
end my_package;
  
package body my_package is

function "and" (a, b: qit) return qit is
type qit_table is array(qit,qit) of qit;
constant qit_and_table : qit_table :=
--  x   0   1   z
( 
  ('x','0','x','x'),-- x
  ('0','0','0','0'),-- 0
  ('x','0','1','x'),-- 1  
  ('x','0','x','x'));--z
  
  begin
    return qit_and_table(a,b);
  end "and";
  
  function to_vector (val: integer) return bit_vector is
        variable vec: bit_vector (0 to 31);
        variable a: integer;
    begin
        a := val;
        for i in 31 downto 0 loop
            if ((a mod 2) = 1) then
                vec(i) := '1';
            else
                vec(i) := '0';
            end if;
            a := a / 2;
        end loop;
        return vec;
    end to_vector;
end my_package;
  
 
