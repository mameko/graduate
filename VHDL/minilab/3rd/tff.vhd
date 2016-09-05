entity tff is
	port( t, clk, rstb : in bit;
	  q, qb : out bit);
end entity;

architecture struct of tff is
	component dff is
		port(d, rstb, clk : in bit;
			q, not_q : out bit);
	end component;
	
	for all: dff use entity work.dff(dataflow);
	signal imq, imt : bit;
	
	begin	  
	  c1: dff port map(imt,rstb,clk,imq,qb);
	  
	  imt <= '1', imq xor t after 1ns;
	  q <= imq;
	
	end struct;
