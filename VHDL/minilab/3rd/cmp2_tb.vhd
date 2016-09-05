entity cmp2_tb is
end entity;

architecture io of cmp2_tb is
	component cmp2 is
		port(c : in bit_vector (5 downto 0);
		 done : out bit);
	end component;
	
	for all: cmp2 use entity work.cmp2(struct);
	
	signal c : bit_vector(5 downto 0);
	signal done : bit;
	
	begin
		c1: cmp2 port map(c,done);
		
		c <= "000000","000100" after 50ns,"100100" after 100ns,"000000" after 150ns, "101010" after 200ns;
end io;
