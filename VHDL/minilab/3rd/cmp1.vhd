entity cmp1 is
	port (a,b :in bit;
		  add0, sel : out bit);
end entity;

architecture struct of cmp1 is
	component xor_gate is
		port(a,b: in bit;
		c: out bit);
	end component;
	
	component not_gate is
		port(a: in bit;
       c: out bit);
	end component;
	
	component and2 is
		port(a, b : in bit;
      c : out bit);
	end component;
	
	for all: xor_gate use entity work.xor_gate(dataflow);
	for all: not_gate use entity work.not_gate(dataflow);
	for all: and2 use entity work.and2(dataflow);
	
	signal not_a : bit;
	
	begin 
		c1: xor_gate port map(a,b,add0);
		c2: not_gate port map(a,not_a);
		c3: and2 port map(not_a,b,sel);
end struct;
	
	
	
	  
