library ieee;
use ieee.std_logic_1164.all;

package  my_package is
  type qit is ('x','0','1','z');
    function "and" (a,b : qit) return qit;
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
  
end my_package;
  
 
