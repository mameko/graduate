entity controller_for_booth_multiplier is
	port (start, done, ai, ai_1, clk, rstb : in bit;
		  sp, sa : out bit_vector;
		  add0, sel : out bit);
end entity;

architecture behaviour of controller_for_booth_multiplier is
	 type mystate is (s0,s1,s2,s3,s4,s5,s6);
	 
	 signal state, nstate : mystate;
	 
	 begin
		transition : process(clk, rstb)
			begin
				if(rstb = '0') then 
					state <= s0;
				elsif (clk = '1' and not clk'stable) then
					state <= nstate;
				end if;
		end process transition;
		
		decoding : process(state,clk)
			begin
				case state is
					when s0 =>
						if( done = '0') then --counter != 0 not done
							nstate <= s1;
						else
							nstate <= s0;
						end if;
					when s1 =>
						if(ai = ai_1) then
							nstate <= s2;
						elsif(ai > ai_1) then
								nstate <= s3;
						else nstate <= s4;
						end if;
					when s2 =>
						nstate <= s5;
					when s3 =>
						nstate <= s5;
					when s4 =>
						nstate <= s5;
					when s5 =>
						if( done = '1') then
						  nstate <= s6;
						elsif(ai = ai_1) then
								nstate <= s2;
						elsif(ai > ai_1) then
								nstate <= s3;
						else nstate <= s4;							
						end if;
					when s6 => 
						if( start = '0') then
							nstate <= s6;
						else
							nstate <= s0;
						end if;
					when others =>
						nstate <= s0;
				end case;
		end process decoding;
		
		output: process (state,clk)
			begin
				case state is
					when s0 => 
						sa <= "00";
						sp <= "00";
					when s1 =>
						sa <= "11";
						sp <= "00";	
					when s2 =>
						sa <= "11";
						sp <= "11";
						add0 <= '0';
						sel <= '0';
					when s3 =>
						sa <= "11";
						sp <= "11";
						add0 <= '1';
						sel <= '1';
					when s4 =>
						sa <= "11";
						sp <= "11";
						add0 <= '1';
						sel <= '0';
					when s5 =>
						sa <= "01";
						sp <= "01";
					when s6 =>
						sa <= "11";
						sp <= "11";
				end case;
		end process output;

end behaviour;						
							
						
