entity controller_for_booth_multiplier_tb is
end entity;

architecture io of controller_for_booth_multiplier_tb is
	component controller_for_booth_multiplier is
		port (start, done, ai, ai_1, clk, rstb : in bit;
			  sp, sa : out bit_vector(1 downto 0);
			  add0, sel : out bit);
	end component;
	
	for all: controller_for_booth_multiplier use entity work.controller_for_booth_multiplier(behaviour);
	
	signal start, done, ai, ai_1, clk, rstb : bit;
	signal sp, sa : bit_vector(1 downto 0);
	signal add0, sel : bit;
	
	begin
		c1: controller_for_booth_multiplier port map (start, done, ai, ai_1, clk, rstb, sp, sa, add0, sel);
		
		start <= '0', '1' after 550ns;
		done <= '0', '1' after 500ns,'0' after 736ns;
		ai <= '1','0' after 100ns,'1' after 250ns, '0' after 470ns;
		ai_1 <= '0','1' after 100ns, '1' after 250ns, '0'after 470ns;
		clk <= not clk after 30ns;
		rstb <= '1';
		
end io;