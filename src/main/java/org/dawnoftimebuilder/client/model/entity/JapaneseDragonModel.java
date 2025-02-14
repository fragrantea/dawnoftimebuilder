package org.dawnoftimebuilder.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;	
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.entity.JapaneseDragonEntity;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal")
public class JapaneseDragonModel extends EntityModel<JapaneseDragonEntity> {

	private float dragonScale = 0;
	private final ModelRenderer HeadCenter;
	private final ModelRenderer NoseA;
	private final ModelRenderer NoseB;
	private final ModelRenderer NoseC;
	private final ModelRenderer NoseD;
	private final ModelRenderer LEye;
	private final ModelRenderer LEyebrow;
	private final ModelRenderer REye;
	private final ModelRenderer REyebrow;
	private final ModelRenderer JawTop;
	private final ModelRenderer HeadBottom;
	private final ModelRenderer LHorneA;
	private final ModelRenderer LHorneB;
	private final ModelRenderer RHorneA;
	private final ModelRenderer RHorneB;
	private final ModelRenderer LMustaA;
	private final ModelRenderer RMustaA;
	private final ModelRenderer BodyA;
	private final ModelRenderer JawBottom;
	private final ModelRenderer Tongue;
	private final ModelRenderer LMustaB;
	private final ModelRenderer LMustaC;
	private final ModelRenderer LMustaD;
	private final ModelRenderer LMustaE;
	private final ModelRenderer RMustaB;
	private final ModelRenderer RMustaC;
	private final ModelRenderer RMustaD;
	private final ModelRenderer RMustaE;
	private final ModelRenderer BodyAHorne;
	private final ModelRenderer BodyB;
	private final ModelRenderer BodyBHorne;
	private final ModelRenderer BodyC;
	private final ModelRenderer BodyCHorne;
	private final ModelRenderer ShoulderL;
	private final ModelRenderer ShoulderR;
	private final ModelRenderer BodyD;
	private final ModelRenderer HarmL;
	private final ModelRenderer FingerLA;
	private final ModelRenderer FingerLB;
	private final ModelRenderer FingerLC;
	private final ModelRenderer FingerLAEnd;
	private final ModelRenderer FingerLBEnd;
	private final ModelRenderer HarmR;
	private final ModelRenderer FingerRA;
	private final ModelRenderer FingerRB;
	private final ModelRenderer FingerRC;
	private final ModelRenderer FingerRAEnd;
	private final ModelRenderer FingerRBEnd;
	private final ModelRenderer BodyDHorne;
	private final ModelRenderer BodyE;
	private final ModelRenderer BodyEHorne;
	private final ModelRenderer BodyF;
	private final ModelRenderer BodyFHorne;
	private final ModelRenderer BodyG;
	private final ModelRenderer BodyGHorne;
	private final ModelRenderer BodyH;
	private final ModelRenderer BodyHHorne;
	private final ModelRenderer ThighR;
	private final ModelRenderer ThighL;
	private final ModelRenderer BodyI;
	private final ModelRenderer LegR;
	private final ModelRenderer HeelR;
	private final ModelRenderer FingerR;
	private final ModelRenderer LegL;
	private final ModelRenderer HeelL;
	private final ModelRenderer FingerL;
	private final ModelRenderer BodyIHorne;
	private final ModelRenderer BodyJ;
	private final ModelRenderer BodyJHorne;
	private final ModelRenderer BodyK;
	private final ModelRenderer BodyKHorne;

	public JapaneseDragonModel() {
		this.texWidth = 128;
		this.texHeight = 64;

		this.FingerRAEnd = new ModelRenderer(this, 32, 2);
		this.FingerRAEnd.setPos(4.0F, 0.0F, 0.0F);
		this.FingerRAEnd.addBox(0.0F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
		this.setRotateAngle(FingerRAEnd, 0.0F, 0.0F, 0.8196066167365371F);
		this.BodyH = new ModelRenderer(this, 0, 0);
		this.BodyH.setPos(0.0F, 0.0F, 16.0F);
		this.BodyH.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 16, 0.0F);
		this.LMustaD = new ModelRenderer(this, 18, 45);
		this.LMustaD.setPos(0.0F, 0.0F, 8.0F);
		this.LMustaD.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 8, 0.0F);
		this.setRotateAngle(LMustaD, 0.0F, -0.4553564018453205F, 0.0F);
		this.FingerRB = new ModelRenderer(this, 32, 0);
		this.FingerRB.setPos(1.5F, 0.5F, 10.0F);
		this.FingerRB.addBox(0.0F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
		this.setRotateAngle(FingerRB, -0.7853981633974483F, -2.0943951023931953F, -0.6108652381980153F);
		this.LegL = new ModelRenderer(this, 78, 45);
		this.LegL.setPos(2.5F, 0.0F, 5.5F);
		this.LegL.addBox(0.0F, -1.5F, 0.0F, 3, 3, 6, 0.0F);
		this.setRotateAngle(LegL, 0.0F, 1.2292353921796064F, 3.141592653589793F);
		this.HarmR = new ModelRenderer(this, 32, 51);
		this.HarmR.setPos(-2.0F, 0.0F, 10.0F);
		this.HarmR.addBox(0.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F);
		this.setRotateAngle(HarmR, 0.0F, 1.2292353921796064F, 0.0F);
		this.RMustaA = new ModelRenderer(this, 18, 45);
		this.RMustaA.setPos(-2.0F, -0.5F, -19.5F);
		this.RMustaA.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 8, 0.0F);
		this.setRotateAngle(RMustaA, 0.0F, 0.0F, 2.792526803190927F);
		this.FingerLBEnd = new ModelRenderer(this, 32, 2);
		this.FingerLBEnd.setPos(4.0F, 0.0F, 0.0F);
		this.FingerLBEnd.addBox(0.0F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
		this.setRotateAngle(FingerLBEnd, 0.0F, 0.0F, -0.9105382707654417F);
		this.LegR = new ModelRenderer(this, 78, 45);
		this.LegR.setPos(2.5F, 0.0F, 5.5F);
		this.LegR.addBox(0.0F, -1.5F, 0.0F, 3, 3, 6, 0.0F);
		this.setRotateAngle(LegR, 0.0F, 1.2292353921796064F, 3.141592653589793F);
		this.Tongue = new ModelRenderer(this, 96, 29);
		this.Tongue.setPos(3.5F, 3.5F, -14.0F);
		this.Tongue.addBox(0.0F, 0.0F, 0.0F, 3, 2, 11, 0.0F);
		this.setRotateAngle(Tongue, 0.08726646259971647F, 0.0F, 0.0F);
		this.RHorneA = new ModelRenderer(this, 60, 7);
		this.RHorneA.setPos(-1.0F, -3.0F, -9.0F);
		this.RHorneA.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
		this.setRotateAngle(RHorneA, -1.7453292519943295F, 2.792526803190927F, 0.17453292519943295F);
		this.FingerRA = new ModelRenderer(this, 32, 0);
		this.FingerRA.setPos(1.5F, -0.5F, 10.0F);
		this.FingerRA.addBox(0.0F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
		this.setRotateAngle(FingerRA, 0.7853981633974483F, -2.0943951023931953F, 0.6108652381980153F);
		this.HeelL = new ModelRenderer(this, 106, 49);
		this.HeelL.setPos(0.0F, 0.0F, 6.0F);
		this.HeelL.addBox(0.0F, -1.0F, 0.0F, 2, 2, 8, 0.0F);
		this.setRotateAngle(HeelL, 0.0F, 2.231054382824351F, 3.141592653589793F);
		this.FingerLA = new ModelRenderer(this, 32, 0);
		this.FingerLA.setPos(1.5F, -0.5F, 10.0F);
		this.FingerLA.addBox(0.0F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
		this.setRotateAngle(FingerLA, 0.7853981633974483F, -2.0943951023931953F, 0.6108652381980153F);
		this.BodyGHorne = new ModelRenderer(this, 58, -16);
		this.BodyGHorne.setPos(0.0F, -6.0F, 0.0F);
		this.BodyGHorne.addBox(0.0F, 0.0F, 0.0F, 0, 2, 16, 0.0F);
		this.REyebrow = new ModelRenderer(this, 32, 10);
		this.REyebrow.setPos(-2.0F, -2.5F, -12.5F);
		this.REyebrow.addBox(0.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
		this.setRotateAngle(REyebrow, -2.1816615649929116F, -2.6186920096922917F, 2.2689280275926285F);
		this.FingerRC = new ModelRenderer(this, 0, 14);
		this.FingerRC.setPos(0.5F, 0.5F, 10.0F);
		this.FingerRC.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(FingerRC, 0.0F, -3.141592653589793F, 0.0F);
		this.LEyebrow = new ModelRenderer(this, 32, 7);
		this.LEyebrow.setPos(2.0F, -2.5F, -12.5F);
		this.LEyebrow.addBox(0.0F, 0.0F, 0.0F, 5, 1, 2, 0.0F);
		this.setRotateAngle(LEyebrow, 0.9599310885968813F, -0.5235987755982988F, -0.3490658503988659F);
		this.HeelR = new ModelRenderer(this, 106, 49);
		this.HeelR.setPos(0.0F, 0.0F, 6.0F);
		this.HeelR.addBox(0.0F, -1.0F, 0.0F, 2, 2, 8, 0.0F);
		this.setRotateAngle(HeelR, 0.0F, 2.231054382824351F, 3.141592653589793F);
		this.BodyCHorne = new ModelRenderer(this, 58, -16);
		this.BodyCHorne.setPos(0.0F, -6.0F, 0.0F);
		this.BodyCHorne.addBox(0.0F, 0.0F, 0.0F, 0, 2, 16, 0.0F);
		this.BodyFHorne = new ModelRenderer(this, 58, -16);
		this.BodyFHorne.setPos(0.0F, -6.0F, 0.0F);
		this.BodyFHorne.addBox(0.0F, 0.0F, 0.0F, 0, 2, 16, 0.0F);
		this.BodyAHorne = new ModelRenderer(this, 58, -16);
		this.BodyAHorne.setPos(0.0F, -6.0F, 0.0F);
		this.BodyAHorne.addBox(0.0F, 0.0F, 0.0F, 0, 2, 16, 0.0F);
		this.BodyJ = new ModelRenderer(this, 60, 4);
		this.BodyJ.setPos(0.0F, 0.0F, 16.0F);
		this.BodyJ.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 16, 0.0F);
		this.REye = new ModelRenderer(this, 0, 4);
		this.REye.setPos(-2.0F, -3.0F, -11.5F);
		this.REye.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
		this.setRotateAngle(REye, 0.5235987755982988F, 0.0F, 1.5707963267948966F);
		this.ShoulderR = new ModelRenderer(this, 86, 44);
		this.ShoulderR.setPos(-4.0F, 0.0F, 8.0F);
		this.ShoulderR.addBox(-2.0F, -1.5F, 0.0F, 4, 3, 10, 0.0F);
		this.setRotateAngle(ShoulderR, 0.36425021489121656F, 1.3203415791337103F, 1.9577358219620393F);
		this.BodyJHorne = new ModelRenderer(this, 58, -16);
		this.BodyJHorne.setPos(0.0F, -4.0F, 0.0F);
		this.BodyJHorne.addBox(0.0F, 0.0F, 0.0F, 0, 2, 16, 0.0F);
		this.RMustaE = new ModelRenderer(this, 28, 46);
		this.RMustaE.setPos(0.0F, 0.0F, 8.0F);
		this.RMustaE.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 6, 0.0F);
		this.setRotateAngle(RMustaE, 0.0F, 0.5462880558742251F, 0.0F);
		this.BodyC = new ModelRenderer(this, 0, 0);
		this.BodyC.setPos(0.0F, 0.0F, 16.0F);
		this.BodyC.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 16, 0.0F);
		this.NoseC = new ModelRenderer(this, 0, 8);
		this.NoseC.setPos(-2.0F, 0.0F, -16.5F);
		this.NoseC.addBox(0.0F, 0.0F, 0.0F, 4, 3, 3, 0.0F);
		this.setRotateAngle(NoseC, 0.7853981633974483F, 0.0F, 0.0F);
		this.RMustaB = new ModelRenderer(this, 18, 45);
		this.RMustaB.setPos(0.0F, 0.0F, 8.0F);
		this.RMustaB.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 8, 0.0F);
		this.setRotateAngle(RMustaB, 0.0F, -0.22759093446006054F, 0.0F);
		this.RMustaC = new ModelRenderer(this, 18, 45);
		this.RMustaC.setPos(0.0F, 0.0F, 8.0F);
		this.RMustaC.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 8, 0.0F);
		this.setRotateAngle(RMustaC, 0.0F, -0.31869712141416456F, 0.0F);
		this.BodyEHorne = new ModelRenderer(this, 58, -16);
		this.BodyEHorne.setPos(0.0F, -6.0F, 0.0F);
		this.BodyEHorne.addBox(0.0F, 0.0F, 0.0F, 0, 2, 16, 0.0F);
		this.HeadCenter = new ModelRenderer(this, 0, 24);
		this.HeadCenter.setPos(0.0F, 1.0F, 8.0F);
		this.HeadCenter.addBox(-4.5F, -4.0F, -10.0F, 9, 6, 10, 0.0F);
		this.NoseD = new ModelRenderer(this, 104, 15);
		this.NoseD.setPos(-2.0F, 0.0F, -14.0F);
		this.NoseD.addBox(0.0F, 0.0F, 0.0F, 4, 3, 6, 0.0F);
		this.setRotateAngle(NoseD, 0.7853981633974483F, 0.0F, 0.0F);
		this.FingerLC = new ModelRenderer(this, 0, 14);
		this.FingerLC.setPos(0.5F, 0.5F, 10.0F);
		this.FingerLC.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(FingerLC, 0.0F, -3.141592653589793F, 0.0F);
		this.BodyDHorne = new ModelRenderer(this, 58, -16);
		this.BodyDHorne.setPos(0.0F, -6.0F, 0.0F);
		this.BodyDHorne.addBox(0.0F, 0.0F, 0.0F, 0, 2, 16, 0.0F);
		this.BodyK = new ModelRenderer(this, 84, 8);
		this.BodyK.setPos(0.0F, 0.0F, 16.0F);
		this.BodyK.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 16, 0.0F);
		this.LHorneB = new ModelRenderer(this, 104, 5);
		this.LHorneB.setPos(4.5F, -5.0F, -1.0F);
		this.LHorneB.addBox(0.0F, 0.0F, 0.0F, 2, 2, 8, 0.0F);
		this.setRotateAngle(LHorneB, 0.17453292519943295F, 0.08726646259971647F, 0.0F);
		this.BodyG = new ModelRenderer(this, 0, 0);
		this.BodyG.setPos(0.0F, 0.0F, 16.0F);
		this.BodyG.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 16, 0.0F);
		this.NoseA = new ModelRenderer(this, 44, 0);
		this.NoseA.setPos(-2.5F, 0.0F, -22.0F);
		this.NoseA.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
		this.setRotateAngle(NoseA, 0.7853981633974483F, 0.0F, 0.0F);
		this.LMustaC = new ModelRenderer(this, 18, 45);
		this.LMustaC.setPos(0.0F, 0.0F, 8.0F);
		this.LMustaC.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 8, 0.0F);
		this.setRotateAngle(LMustaC, 0.0F, -0.31869712141416456F, 0.0F);
		this.LMustaE = new ModelRenderer(this, 28, 46);
		this.LMustaE.setPos(0.0F, 0.0F, 8.0F);
		this.LMustaE.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 6, 0.0F);
		this.setRotateAngle(LMustaE, 0.0F, 0.5462880558742251F, 0.0F);
		this.ThighR = new ModelRenderer(this, 56, 51);
		this.ThighR.setPos(-4.0F, 0.0F, 8.0F);
		this.ThighR.addBox(-2.5F, -2.0F, -2.5F, 5, 4, 8, 0.0F);
		this.setRotateAngle(ThighR, 0.18203784098300857F, 1.593485607070823F, 2.1399481958702475F);
		this.RMustaD = new ModelRenderer(this, 18, 45);
		this.RMustaD.setPos(0.0F, 0.0F, 8.0F);
		this.RMustaD.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 8, 0.0F);
		this.setRotateAngle(RMustaD, 0.0F, -0.4553564018453205F, 0.0F);
		this.FingerLAEnd = new ModelRenderer(this, 32, 2);
		this.FingerLAEnd.setPos(4.0F, 0.0F, 0.0F);
		this.FingerLAEnd.addBox(0.0F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
		this.setRotateAngle(FingerLAEnd, 0.0F, 0.0F, 0.8196066167365371F);
		this.BodyB = new ModelRenderer(this, 0, 0);
		this.BodyB.setPos(0.0F, 0.0F, 16.0F);
		this.BodyB.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 16, 0.0F);
		this.JawTop = new ModelRenderer(this, 26, 30);
		this.JawTop.setPos(-3.5F, 0.0F, -22.0F);
		this.JawTop.addBox(0.0F, 0.0F, 0.0F, 7, 3, 12, 0.0F);
		this.BodyI = new ModelRenderer(this, 32, 8);
		this.BodyI.setPos(0.0F, 0.0F, 16.0F);
		this.BodyI.addBox(-3.0F, -3.0F, 0.0F, 6, 6, 16, 0.0F);
		this.LMustaB = new ModelRenderer(this, 18, 45);
		this.LMustaB.setPos(0.0F, 0.0F, 8.0F);
		this.LMustaB.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 8, 0.0F);
		this.setRotateAngle(LMustaB, 0.0F, -0.22759093446006054F, 0.0F);
		this.BodyD = new ModelRenderer(this, 0, 0);
		this.BodyD.setPos(0.0F, 0.0F, 16.0F);
		this.BodyD.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 16, 0.0F);
		this.HarmL = new ModelRenderer(this, 32, 51);
		this.HarmL.setPos(-2.0F, 0.0F, 10.0F);
		this.HarmL.addBox(0.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F);
		this.setRotateAngle(HarmL, 0.0F, 1.2292353921796064F, 0.0F);
		this.BodyKHorne = new ModelRenderer(this, 36, 21);
		this.BodyKHorne.setPos(0.0F, -3.0F, 0.0F);
		this.BodyKHorne.addBox(0.0F, 0.0F, 0.0F, 0, 6, 24, 0.0F);
		this.LHorneA = new ModelRenderer(this, 0, 40);
		this.LHorneA.setPos(1.0F, -3.0F, -9.0F);
		this.LHorneA.addBox(0.0F, 0.0F, 0.0F, 3, 3, 10, 0.0F);
		this.setRotateAngle(LHorneA, 0.17453292519943295F, 0.3490658503988659F, -0.17453292519943295F);
		this.NoseB = new ModelRenderer(this, 0, 8);
		this.NoseB.setPos(-2.0F, 0.0F, -18.5F);
		this.NoseB.addBox(0.0F, 0.0F, 0.0F, 4, 3, 3, 0.0F);
		this.setRotateAngle(NoseB, 0.7853981633974483F, 0.0F, 0.0F);
		this.BodyF = new ModelRenderer(this, 0, 0);
		this.BodyF.setPos(0.0F, 0.0F, 16.0F);
		this.BodyF.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 16, 0.0F);
		this.ShoulderL = new ModelRenderer(this, 86, 44);
		this.ShoulderL.setPos(4.0F, 0.0F, 8.0F);
		this.ShoulderL.addBox(-2.0F, -1.5F, 0.0F, 4, 3, 10, 0.0F);
		this.setRotateAngle(ShoulderL, 0.0F, 1.4114477660878142F, 1.1838568316277536F);
		this.ThighL = new ModelRenderer(this, 56, 51);
		this.ThighL.setPos(4.0F, 0.0F, 8.0F);
		this.ThighL.addBox(-2.5F, -2.0F, -2.5F, 5, 4, 8, 0.0F);
		this.setRotateAngle(ThighL, 0.7740535232594852F, 1.593485607070823F, 2.1399481958702475F);
		this.FingerLB = new ModelRenderer(this, 32, 0);
		this.FingerLB.setPos(1.5F, 0.5F, 10.0F);
		this.FingerLB.addBox(0.0F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
		this.setRotateAngle(FingerLB, -0.7853981633974483F, -2.0943951023931953F, -0.6108652381980153F);
		this.FingerL = new ModelRenderer(this, 84, 12);
		this.FingerL.setPos(1.5F, 0.0F, 8.0F);
		this.FingerL.addBox(0.0F, -1.5F, 0.0F, 1, 3, 5, 0.0F);
		this.setRotateAngle(FingerL, 0.0F, -1.4570008595648662F, 0.0F);
		this.BodyA = new ModelRenderer(this, 0, 0);
		this.BodyA.setPos(0.0F, 1.0F, 7.0F);
		this.BodyA.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 16, 0.0F);
		this.LMustaA = new ModelRenderer(this, 18, 45);
		this.LMustaA.setPos(2.0F, -0.5F, -19.5F);
		this.LMustaA.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 8, 0.0F);
		this.setRotateAngle(LMustaA, 0.0F, 0.0F, 0.3490658503988659F);
		this.FingerRBEnd = new ModelRenderer(this, 32, 2);
		this.FingerRBEnd.setPos(4.0F, 0.0F, 0.0F);
		this.FingerRBEnd.addBox(0.0F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
		this.setRotateAngle(FingerRBEnd, 0.0F, 0.0F, -0.9105382707654417F);
		this.BodyHHorne = new ModelRenderer(this, 58, -16);
		this.BodyHHorne.setPos(0.0F, -6.0F, 0.0F);
		this.BodyHHorne.addBox(0.0F, 0.0F, 0.0F, 0, 2, 16, 0.0F);
		this.RHorneB = new ModelRenderer(this, 104, 3);
		this.RHorneB.setPos(-4.5F, -5.0F, -1.0F);
		this.RHorneB.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
		this.setRotateAngle(RHorneB, -1.7453292519943295F, 3.0543261909900767F, 0.0F);
		this.JawBottom = new ModelRenderer(this, 68, 26);
		this.JawBottom.setPos(8.0F, 4.0F, 0.0F);
		this.JawBottom.addBox(0.0F, 0.0F, 0.0F, 6, 2, 16, 0.0F);
		this.setRotateAngle(JawBottom, 3.141592653589793F, 0.0F, 3.141592653589793F);
		this.LEye = new ModelRenderer(this, 0, 0);
		this.LEye.setPos(2.0F, -3.0F, -11.5F);
		this.LEye.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
		this.setRotateAngle(LEye, 0.0F, -0.5235987755982988F, 0.0F);
		this.HeadBottom = new ModelRenderer(this, 52, 30);
		this.HeadBottom.setPos(-5.0F, 1.0F, -5.0F);
		this.HeadBottom.addBox(0.0F, -1.0F, -3.0F, 10, 6, 6, 0.0F);
		this.setRotateAngle(HeadBottom, -0.08726646259971647F, 0.0F, 0.0F);
		this.BodyE = new ModelRenderer(this, 0, 0);
		this.BodyE.setPos(0.0F, 0.0F, 16.0F);
		this.BodyE.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 16, 0.0F);
		this.FingerR = new ModelRenderer(this, 84, 12);
		this.FingerR.setPos(1.5F, 0.0F, 8.0F);
		this.FingerR.addBox(0.0F, -1.5F, 0.0F, 1, 3, 5, 0.0F);
		this.setRotateAngle(FingerR, 0.0F, -1.4570008595648662F, 0.0F);
		this.BodyIHorne = new ModelRenderer(this, 58, -16);
		this.BodyIHorne.setPos(0.0F, -5.0F, 0.0F);
		this.BodyIHorne.addBox(0.0F, 0.0F, 0.0F, 0, 2, 16, 0.0F);
		this.BodyBHorne = new ModelRenderer(this, 58, -16);
		this.BodyBHorne.setPos(0.0F, -6.0F, 0.0F);
		this.BodyBHorne.addBox(0.0F, 0.0F, 0.0F, 0, 2, 16, 0.0F);

		this.FingerRA.addChild(this.FingerRAEnd);
		this.BodyG.addChild(this.BodyH);
		this.LMustaC.addChild(this.LMustaD);
		this.HarmR.addChild(this.FingerRB);
		this.ThighL.addChild(this.LegL);
		this.ShoulderR.addChild(this.HarmR);
		this.HeadCenter.addChild(this.RMustaA);
		this.FingerLB.addChild(this.FingerLBEnd);
		this.ThighR.addChild(this.LegR);
		this.HeadBottom.addChild(this.Tongue);
		this.HeadCenter.addChild(this.RHorneA);
		this.HarmR.addChild(this.FingerRA);
		this.LegL.addChild(this.HeelL);
		this.HarmL.addChild(this.FingerLA);
		this.BodyG.addChild(this.BodyGHorne);
		this.HeadCenter.addChild(this.REyebrow);
		this.HarmR.addChild(this.FingerRC);
		this.HeadCenter.addChild(this.LEyebrow);
		this.LegR.addChild(this.HeelR);
		this.BodyC.addChild(this.BodyCHorne);
		this.BodyF.addChild(this.BodyFHorne);
		this.BodyA.addChild(this.BodyAHorne);
		this.BodyI.addChild(this.BodyJ);
		this.HeadCenter.addChild(this.REye);
		this.BodyC.addChild(this.ShoulderR);
		this.BodyJ.addChild(this.BodyJHorne);
		this.RMustaD.addChild(this.RMustaE);
		this.BodyB.addChild(this.BodyC);
		this.HeadCenter.addChild(this.NoseC);
		this.RMustaA.addChild(this.RMustaB);
		this.RMustaB.addChild(this.RMustaC);
		this.BodyE.addChild(this.BodyEHorne);
		this.HeadCenter.addChild(this.NoseD);
		this.HarmL.addChild(this.FingerLC);
		this.BodyD.addChild(this.BodyDHorne);
		this.BodyJ.addChild(this.BodyK);
		this.HeadCenter.addChild(this.LHorneB);
		this.BodyF.addChild(this.BodyG);
		this.HeadCenter.addChild(this.NoseA);
		this.LMustaB.addChild(this.LMustaC);
		this.LMustaD.addChild(this.LMustaE);
		this.BodyH.addChild(this.ThighR);
		this.RMustaC.addChild(this.RMustaD);
		this.FingerLA.addChild(this.FingerLAEnd);
		this.BodyA.addChild(this.BodyB);
		this.HeadCenter.addChild(this.JawTop);
		this.BodyH.addChild(this.BodyI);
		this.LMustaA.addChild(this.LMustaB);
		this.BodyC.addChild(this.BodyD);
		this.ShoulderL.addChild(this.HarmL);
		this.BodyK.addChild(this.BodyKHorne);
		this.HeadCenter.addChild(this.LHorneA);
		this.HeadCenter.addChild(this.NoseB);
		this.BodyE.addChild(this.BodyF);
		this.BodyC.addChild(this.ShoulderL);
		this.BodyH.addChild(this.ThighL);
		this.HarmL.addChild(this.FingerLB);
		this.HeelL.addChild(this.FingerL);
		this.HeadCenter.addChild(this.LMustaA);
		this.FingerRB.addChild(this.FingerRBEnd);
		this.BodyH.addChild(this.BodyHHorne);
		this.HeadCenter.addChild(this.RHorneB);
		this.HeadBottom.addChild(this.JawBottom);
		this.HeadCenter.addChild(this.LEye);
		this.HeadCenter.addChild(this.HeadBottom);
		this.BodyD.addChild(this.BodyE);
		this.HeelR.addChild(this.FingerR);
		this.BodyI.addChild(this.BodyIHorne);
		this.BodyB.addChild(this.BodyBHorne);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha){
		matrixStack.pushPose();
		matrixStack.scale(this.dragonScale, this.dragonScale, this.dragonScale);
		this.HeadCenter.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		this.BodyA.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		matrixStack.popPose();
	}

	@Override
	public void setupAnim(JapaneseDragonEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if(dragonScale == 0) this.dragonScale = entityIn.getDragonSize();
		float PI = (float)Math.PI;
		float rotation = entityIn.getHeadTargetAngle();
		float moveProgress = entityIn.getAnimationLoop(ageInTicks);

		float offset = -0.75F + 1.5F / this.dragonScale;
		this.HeadCenter.y += offset;
		this.BodyA.y += offset;
		this.BodyA.xRot -= rotation;
		this.HeadCenter.xRot = headPitch * 0.010F - rotation;
		this.HeadCenter.yRot = netHeadYaw * 0.006F;
		this.HeadCenter.x = 0.25F * sinPI(1 + moveProgress);
		this.HeadCenter.y = 0.15F * sinPI(2.0F * moveProgress);
		this.BodyA.x = 0.25F * sinPI(1 + moveProgress);
		this.BodyA.y = 0.15F * sinPI(2.0F * moveProgress);
		this.BodyA.xRot = sinPI((moveProgress + 0.25F) * 2) * 0.135F * PI;
		this.BodyA.yRot = (sinPI(moveProgress + 0.25F) * 0.21F + 0.2F) * PI;
		this.BodyB.xRot = sinPI((moveProgress - 0.17F) * 2) * 0.35F * PI;
		this.BodyB.yRot = sinPI(moveProgress - 0.17F) * 0.08F * PI;
		this.LMustaA.yRot = (sinPI(moveProgress * 2) * 0.05F + 0.2F) * PI;
		this.RMustaA.yRot = (sinPI(moveProgress * 2) * 0.05F + 0.2F) * PI;
		moveProgress = moveProgress - 0.25F;
		this.LMustaB.yRot = (sinPI(moveProgress * 2) * 0.1F) * PI;
		this.RMustaB.yRot = (sinPI(moveProgress * 2) * 0.1F) * PI;
		moveProgress = moveProgress - 0.25F;
		this.BodyC.xRot = sinPI(moveProgress * 2) * PI * 0.35F;
		this.BodyC.yRot = sinPI(moveProgress) * PI * 0.2F;
		this.LMustaC.yRot = (sinPI(moveProgress * 2) * 0.2F) * PI;
		this.RMustaC.yRot = (sinPI(moveProgress * 2) * 0.2F) * PI;
		moveProgress = moveProgress - 0.25F;
		this.BodyD.xRot = sinPI(moveProgress * 2) * PI * 0.35F;
		this.BodyD.yRot = sinPI(moveProgress) * PI * 0.2F;
		this.LMustaD.yRot = (sinPI(moveProgress * 2) * 0.2F) * PI;
		this.RMustaD.yRot = (sinPI(moveProgress * 2) * 0.2F) * PI;
		moveProgress = moveProgress - 0.25F;
		this.BodyE.xRot = sinPI(moveProgress * 2) * PI * 0.35F;
		this.BodyE.yRot = sinPI(moveProgress) * PI * 0.2F;
		this.LMustaE.yRot = (sinPI(moveProgress * 2) * 0.2F) * PI;
		this.RMustaE.yRot = (sinPI(moveProgress * 2) * 0.2F) * PI;
		moveProgress = moveProgress - 0.25F;
		this.BodyF.xRot = sinPI(moveProgress * 2) * PI * 0.35F;
		this.BodyF.yRot = sinPI(moveProgress) * PI * 0.2F;
		moveProgress = moveProgress - 0.25F;
		this.BodyG.xRot = sinPI(moveProgress * 2) * PI * 0.35F;
		this.BodyG.yRot = sinPI(moveProgress) * PI * 0.2F;
		moveProgress = moveProgress - 0.25F;
		this.BodyH.xRot = sinPI(moveProgress * 2) * PI * 0.35F;
		this.BodyH.yRot = sinPI(moveProgress) * PI * 0.2F;
		moveProgress = moveProgress - 0.25F;
		this.BodyI.xRot = sinPI(moveProgress * 2) * PI * 0.35F;
		this.BodyI.yRot = sinPI(moveProgress) * PI * 0.2F;
		moveProgress = moveProgress - 0.25F;
		this.BodyJ.xRot = sinPI(moveProgress * 2) * PI * 0.35F;
		this.BodyJ.yRot = sinPI(moveProgress) * PI * 0.2F;
		moveProgress = moveProgress - 0.25F;
		this.BodyK.xRot = sinPI(moveProgress * 2) * PI * 0.35F;
		this.BodyK.yRot = sinPI(moveProgress) * PI * 0.2F;
	}

	private float sinPI(float f) {
		return MathHelper.sin(f * (float)Math.PI);
	}

	private void setRotateAngle(ModelRenderer ModelRenderer, float x, float y, float z) {
		ModelRenderer.xRot = x;
		ModelRenderer.yRot = y;
		ModelRenderer.zRot = z;
	}
}
