entity cmp2 is
	port(c : in bit_vector;
		 done : out bit);
end entity;

architecture struct of cmp2 is
	component and2 is
		port(a, b : in bit;
			c : out bit);
	end component;
	  
	component and3_gate is
		port(a, b, c: in bit;
			d : out bit);
	end component;
	
	component not_gate is
	   port(a: in bit;
       c: out bit);
	end component;

	for all: and2 use entity work.and2(dataflow);
	for all: and3_gate use entity work.and3_gate(dataflow);
	
	signal im_345, im_210 : bit;
	signal imnotc5,imnotc4,imnotc3,imnotc2,imnotc1,imnotc0 : bit;
	
	begin
		c5: not_gate port map(c(5),imnotc5);
		c4: not_gate port map(c(4),imnotc4);
		c3: not_gate port map(c(3),imnotc3);
		c2: not_gate port map(c(2),imnotc2);
		c1: not_gate port map(c(1),imnotc1);
		c0: not_gate port map(c(0),imnotc0);
		
		c6: and3_gate port map(imnotc5,imnotc4,imnotc3,im_345);
		c7: and3_gate port map(imnotc2,imnotc1,imnotc0,im_210);
		
		c8: and2 port map(im_210,im_345,done);
end struct;
		