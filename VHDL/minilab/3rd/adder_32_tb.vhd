library ieee;
use ieee.std_logic_1164.all;
use work.my_package.all;

entity adder_32_tb is
end entity;

architecture io of adder_32_tb is
	component adder_32 is
		port(a,b : in bit_vector (31 downto 0);
			   sum : out bit_vector (31 downto 0));
	end component;
	
	for all: adder_32 use entity work.adder_32(struct);
	
	signal a,b,sum : bit_vector (31 downto 0);
	
	begin	
	  c1: adder_32 port map(a,b,sum);  
		p1: process
		variable i,j : integer;
			begin
				outer_loop: for i in 0 to 10 loop
								a <= to_vector(i);
					inner_loop: for j in 0 to 10 loop
								b <= to_vector(j);
							 wait for 50 ns;
					end loop inner_loop;
				end loop outer_loop;				 			
		end process p1;		
end io;
