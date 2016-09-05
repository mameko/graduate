entity tff_tb is
end entity;

architecture io of tff_tb is
	component tff is
		port(t, clk, rstb : in bit;
			 q, qb : out bit);
	end component;
	
	for all: tff use entity work.tff(struct);
	signal t, clk, rstb, q, qb : bit;
	begin
		c1: tff port map(t,clk,rstb,q,qb);
		
		t <= not t after 5ns;
		clk <= not clk after 8ns;
		rstb <= not rstb after 77ns;
end io;
