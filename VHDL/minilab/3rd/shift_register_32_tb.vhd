library ieee;
--use ieee.std_logic_1164.all;
use work.my_package.all;

entity shift_register_32_tb is
end entity;

architecture io of shift_register_32_tb is
	component shift_register_32 is
		port (l : in bit_vector (31 downto 0);
			  sri, clk, rstb : in bit;
		      s : in bit_vector (1 downto 0);
		      q : out bit_vector (31 downto 0));
	end component;
	
	for all: shift_register_32 use entity work.shift_register_32(struct);
	
	signal l, q :bit_vector (31 downto 0);
	signal sri, clk, rstb : bit;
	signal s : bit_vector(1 downto 0);
	
	begin
		c1: shift_register_32 port map(l,sri,clk,rstb,s,q);
		
		l <= "10000000000001000000111000000000";
		clk <= not clk after 10ns;
		rstb <= '1';
		sri <= '1';
		s <= "00", "01" after 100ns, "01" after 200ns, "11" after 300ns, "01" after 400ns, "10" after 500ns, "11" after 600ns,"10" after 700ns;
		
end io;
		


