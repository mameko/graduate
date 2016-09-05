entity dff_struct is
  port( d, clk, rstb : in bit;
        q, not_q: out bit);
end entity;

architecture struct of dff_struct is
  component nand3_gate is
    port(a,b,c: in bit;
       d: out bit);
  end component;
  
  component nand2_gate is
    port(a,b: in bit;
       c: out bit);
  end component;
  
  for all: nand3_gate use entity work.nand3_gate(dataflow);
  for all: nand2_gate use entity work.nand2_gate(dataflow);

  signal im1,im2,im3,im4,im5,im6 : bit;
  
  begin
    g1: nand3_gate port map(im3,d,rstb,im1);  
    g2: nand2_gate port map(im4,im1,im2);
    g3: nand3_gate port map(im1,clk,im4,im3);
    g4: nand3_gate port map(clk,im2,rstb,im4);
    g5: nand3_gate port map(im3,rstb,im6,im5);
    g6: nand2_gate port map(im5,im4,im6);
      
    q <= im6;
    not_q <= im5;
      
end struct;
    