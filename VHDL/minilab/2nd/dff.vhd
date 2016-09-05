entity dff is
  port( d,clk,rstb : in bit;
        q,not_q : out bit);
end entity;

architecture dataflow of dff is
  begin
  b0: block((clk='1' and not clk'stable) or rstb = '0')
    begin
        q <= guarded d when rstb = '1' else '0';  
       not_q <= guarded not d when rstb = '1' else '1';        
  end block b0;
end dataflow;

    