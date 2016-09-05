entity binary_counter_64 is
	port(o : out bit_vector(5 downto 0));
end entity;

architecture struct of binary_counter_64 is
	component tff is
		port( t, clk, rstb : in bit;
			  q, qb : out bit);
	end component;
	
	for all: tff use entity work.tff(struct);
	signal im : bit_vector (5 downto 0);
	signal one : bit := '1';
	signal rstb : bit := '1';
	signal clk : bit;
	
	begin
		c1: tff port map(one,clk,rstb,im(0));
		c2: tff port map(one,im(0),rstb,im(1));
		c3: tff port map(one,im(1),rstb,im(2));
		c4: tff port map(one,im(2),rstb,im(3));
		c5: tff port map(one,im(3),rstb,im(4));
		c6: tff port map(one,im(4),rstb,im(5));
		
		clk <= not clk after 5ns;
end struct;
