entity cmp1_tb is
end entity;

architecture io of cmp1_tb is
	component cmp1 is
		port (a,b :in bit;
		  add0, sel : out bit);
	end component;
	
	for all: cmp1 use entity work.cmp1(struct);
	
	signal a,b,add0,sel : bit;
	
	begin
		c1: cmp1 port map(a,b,add0,sel);
		
		a <= not a after 10ns;
		b <= not b after 23ns;
end io;
